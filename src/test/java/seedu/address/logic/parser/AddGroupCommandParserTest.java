package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CLASSCODE_DESC_G101;
import static seedu.address.logic.commands.CommandTestUtil.CLASSCODE_DESC_G102;
import static seedu.address.logic.commands.CommandTestUtil.GROUPNAME_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.GROUPNAME_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.GROUPTYPE_DESC_OP1;
import static seedu.address.logic.commands.CommandTestUtil.GROUPTYPE_DESC_OP2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLASSCODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUPNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUPTYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHEDULE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SCHEDULE_DESC_G1O1;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSCODE_G101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUPNAME_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUPTYPE_OP1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_G101;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalTutorialGroups.G101_OP1_1;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddClassCommand;
import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.model.student.ClassCode;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutorialclass.Schedule;
import seedu.address.model.tutorialgroup.GroupName;
import seedu.address.model.tutorialgroup.GroupType;
import seedu.address.model.tutorialgroup.TutorialGroup;
import seedu.address.testutil.TutorialGroupBuilder;

public class AddGroupCommandParserTest {


    private AddGroupCommandParser parser = new AddGroupCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        TutorialGroup expectedTutorialGroup = new TutorialGroupBuilder(G101_OP1_1).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + GROUPNAME_DESC_1 + CLASSCODE_DESC_G101
                + GROUPTYPE_DESC_OP1, new AddGroupCommand(expectedTutorialGroup));

        // multiple classCodes - last classCode accepted
        assertParseSuccess(parser, GROUPNAME_DESC_1 + CLASSCODE_DESC_G102 + CLASSCODE_DESC_G101
                + GROUPTYPE_DESC_OP1, new AddGroupCommand(expectedTutorialGroup));

        // multiple group names - last group name accepted
        assertParseSuccess(parser, GROUPNAME_DESC_2 + GROUPNAME_DESC_1 + CLASSCODE_DESC_G101
                + GROUPTYPE_DESC_OP1, new AddGroupCommand(expectedTutorialGroup));

        // multiple group types - last group type accepted
        assertParseSuccess(parser, GROUPNAME_DESC_1 + CLASSCODE_DESC_G101 + GROUPTYPE_DESC_OP2
                + GROUPTYPE_DESC_OP1, new AddGroupCommand(expectedTutorialGroup));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupCommand.MESSAGE_USAGE);

        // missing ClassCode Prefix
        assertParseFailure(parser, VALID_CLASSCODE_G101 + GROUPNAME_DESC_1 + GROUPTYPE_DESC_OP1, expectedMessage);

        // missing group name prefix
        assertParseFailure(parser, CLASSCODE_DESC_G101 + VALID_GROUPNAME_1 + GROUPNAME_DESC_1, expectedMessage);

        // missing group type prefix
        assertParseFailure(parser, CLASSCODE_DESC_G101 + GROUPNAME_DESC_1 + VALID_GROUPTYPE_OP1, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_CLASSCODE_G101 + VALID_GROUPNAME_1 + VALID_GROUPTYPE_OP1, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid class code
        // assertParseFailure(parser, GROUPNAME_DESC_1 + INVALID_CLASSCODE_DESC + GROUPTYPE_DESC_OP1,
        //        ClassCode.MESSAGE_CONSTRAINTS);

        // invalid group name
        assertParseFailure(parser, INVALID_GROUPNAME_DESC + CLASSCODE_DESC_G101 + GROUPTYPE_DESC_OP1,
                GroupName.MESSAGE_CONSTRAINTS);

        // invalid group type
        assertParseFailure(parser, GROUPNAME_DESC_1 + CLASSCODE_DESC_G101 + INVALID_GROUPTYPE_DESC,
                GroupType.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_GROUPNAME_DESC + CLASSCODE_DESC_G101 + INVALID_GROUPTYPE_DESC,
                GroupName.MESSAGE_CONSTRAINTS);

        // preamble not empty
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + CLASSCODE_DESC_G101 + GROUPNAME_DESC_1 + GROUPTYPE_DESC_OP1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupCommand.MESSAGE_USAGE));
    }
}